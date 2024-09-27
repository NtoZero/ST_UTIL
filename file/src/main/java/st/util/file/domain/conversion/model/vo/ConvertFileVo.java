package st.util.file.domain.conversion.model.vo;

import lombok.Builder;
import lombok.Getter;
import st.util.file.domain.comm.model.vo.FileBaseVo;
import st.util.file.domain.conversion.model.dto.ConvertFileDto;

@Getter
@Builder
public class ConvertFileVo implements FileBaseVo {
    private String directory;

    private String originFilePath;
    private String convertedFilePath;

    @Override
    public String getFileName() {
        return this.convertedFilePath;
    }

    @Override
    public String getFileExtension() {
        return this.convertedFilePath;
    }

    @Override
    public String getFileDirectory() {
        return this.directory;
    }

    public ConvertFileDto convertFileDto() {
        return ConvertFileDto.builder()
                .directory(this.directory)
                .originFileFullPath(this.originFilePath)
                .convertedFileFullPath(this.convertedFilePath)
                .build();
    }
}
