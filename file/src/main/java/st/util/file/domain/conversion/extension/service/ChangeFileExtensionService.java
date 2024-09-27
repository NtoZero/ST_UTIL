package st.util.file.domain.conversion.extension.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.util.file.domain.conversion.model.dto.ChangeExtensionDto;
import st.util.file.domain.conversion.model.dto.ConvertFileDto;
import st.util.file.domain.conversion.model.vo.ConvertFileVo;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChangeFileExtensionService {

    public ChangeExtensionDto.Response convertFileListExtension(ChangeExtensionDto.Request reqDto) {
        Path dirPath = Path.of(reqDto.getDirectoryPath());
        if (!Files.isDirectory(dirPath)) {
            throw new IllegalArgumentException("Incorrect Directory Path.");
        }
        String newExtension = reqDto.getNewExtension();

        List<ConvertFileVo> convertFileVoList = new ArrayList<>();

        // try-with-resources 구문을 사용하여 스트림을 안전하게 닫음
        try (Stream<Path> paths = Files.walk(dirPath, 1, FileVisitOption.FOLLOW_LINKS)) {
            paths.forEach(path -> {
                log.debug(path.toString());
                if (Files.isDirectory(path)) {
                    boolean isDirectoryEndswithNewExt = path.toString().endsWith(newExtension);
                    if (isDirectoryEndswithNewExt) {
                        ConvertFileVo convertFileVo = convertFileExtension(path, newExtension);
                        convertFileVoList.add(convertFileVo);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ChangeExtensionDto.Response.builder()
                .directoryPath(reqDto.getDirectoryPath())
                .convertFileDtoList(convertFileVoList.stream().map(ConvertFileDto::of).toList())
                .build();
    }


    private ConvertFileVo convertFileExtension(Path originPath, String newExtension) {
        try {
            if(Files.isDirectory(originPath)) {
                //디렉토리 삭제
               Files.delete(originPath); //비어있는 디렉토리 삭제, 비어있지 않다면 DirectoryNotEmptyException 발생
                //파일로 생성
               Files.createFile(originPath);
            }

            String fileNameWithoutExtension = getFileNameWithoutExtension(originPath);
            String convertedFilePath = String.join(".", fileNameWithoutExtension, newExtension);

            // 파일 확장자 변경
            Files.move(originPath, Path.of(convertedFilePath));
            log.info("File renamed: From {} To {}", originPath.toString(), convertedFilePath);

            return ConvertFileVo.builder()
                    .originFilePath(originPath.toString())
                    .convertedFilePath(convertedFilePath)
                    .build();
        }catch (DirectoryNotEmptyException e) {
            log.error("{} 디렉토리 내부에 파일이 존재하여 삭제할 수 없습니다.", originPath.toString());
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileNameWithoutExtension(Path path) {
        String pathString = path.toString();

        int lastDotIndex = pathString.lastIndexOf(".");

        String pathStringWithoutExt = pathString;
        if(lastDotIndex != -1) {
            // 확장자 추출
            String extension = pathString.substring(lastDotIndex + 1);
            // 나머지 문자열 추출
            pathStringWithoutExt = pathString.substring(0, lastDotIndex);
        }

        return pathStringWithoutExt;
    }
}
