package wooteco.subway.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.dto.LineRequest;
import wooteco.subway.admin.dto.LineResponse;
import wooteco.subway.admin.dto.LineUpdateRequest;
import wooteco.subway.admin.service.LineService;

import java.net.URI;

@RestController
public class LineController {
    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines")
    public ResponseEntity showLine(@RequestBody LineRequest request) {
        Line line = request.toLine();
        Line persistLine = lineService.save(line);

        return ResponseEntity
                .created(URI.create("/stations/" + persistLine.getId()))
                .body(LineResponse.of(persistLine));
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity showLine(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(lineService.findLine(id));
    }

    @GetMapping("/lines")
    public ResponseEntity showLines() {
        return ResponseEntity.ok().body(lineService.showLines());
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity updateLine(@PathVariable("id") Long id, @RequestBody LineUpdateRequest request) {
        Line line = request.toLine();
        Line updatedLine = lineService.updateLine(id, line);
        return ResponseEntity.ok().body(updatedLine);
    }

}
