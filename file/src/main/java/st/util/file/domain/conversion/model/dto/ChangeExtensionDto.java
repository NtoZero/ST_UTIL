package st.util.file.domain.conversion.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

public class ChangeExtensionDto {

    @Getter
    public static class Request {

        /**
         * directoryPath: 파일이 존재하는 디렉토리 경로
         */
        @NotEmpty
        public String directoryPath;

        /**
         * newExtension: 새 확장자 명
         */
        @NotEmpty
        public String newExtension;

        /**
         * targetFileName: 변경할 파일 이름
         * 해당 변수 값이 존재하지 않는다면 자동으로 해당 디렉토리 내 모든 파일 변환
         */
        public String targetFileName;
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        /**
         * directoryPath: 파일이 존재하는 디렉토리 경로
         */
        public String directoryPath;

        /**
         * newFileFullPathList: 새 파일 경로 목록 (확장자 포함)
         */
        public List<ConvertFileDto> convertFileDtoList;
    }
}
