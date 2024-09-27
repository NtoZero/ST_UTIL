package st.util.file.domain.conversion.model.dto;

import lombok.Builder;
import lombok.Getter;
import st.util.file.domain.conversion.model.vo.ConvertFileVo;

@Getter
@Builder
public class ConvertFileDto {
    private String directory;
    private String originFileFullPath;
    private String convertedFileFullPath;

    public static ConvertFileDto of(ConvertFileVo convertFileVo) {
        return ConvertFileDto.builder()
                .directory(convertFileVo.getDirectory())
                .originFileFullPath(convertFileVo.getOriginFilePath())
                .convertedFileFullPath(convertFileVo.getConvertedFilePath())
                .build();
    }
}
