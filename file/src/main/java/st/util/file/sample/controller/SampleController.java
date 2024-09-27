package st.util.file.sample.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import st.util.file.domain.conversion.model.dto.ChangeExtensionDto;
import st.util.file.domain.conversion.extension.service.ChangeFileExtensionService;

@RequiredArgsConstructor
@RequestMapping("/sample/files")
@RestController
public class SampleController {
    private final ChangeFileExtensionService changeFileExtensionService;

    /**
     *
     * directoryPath (필수값)
     * newExtension (필수값)
     * targetFileName (선택값)
     *
     * 을 받아 targetFileName 이 없을 경우 해당 디렉토리 내의 모든 파일의 확장자명을 변경한다.
     *
     * 현재 구현 사항
     * 1) 새로운 확장자로 이름이 끝나는 '디렉토리'는 해당 확장자로 이름이 끝나는 '파일'로 변경된다.
     *
     * 추후 구현 사항
     * 1) 특정 postfix로 끝나는 파일이나 디렉토리 명만 변경 대상으로 삼는다.
     */
    @PostMapping("/v1/conversion/change-extension")
    public ResponseEntity<ChangeExtensionDto.Response> changeFileExtension(@RequestBody @Valid ChangeExtensionDto.Request reqDto) {

        ChangeExtensionDto.Response resDto = changeFileExtensionService.convertFileListExtension(reqDto);
        return ResponseEntity.ok(resDto);
    }
}
