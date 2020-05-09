package wooteco.subway.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.dto.LineRequest;
import wooteco.subway.admin.dto.LineResponse;
import wooteco.subway.admin.repository.LineRepository;

import java.net.URI;

@RestController
public class LineController {
    private final LineRepository lineRepository;

    public LineController(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @PostMapping("/lines")
    public ResponseEntity createLine(@RequestBody LineRequest request) {
        Line line = request.toLine();
        Line persistLine = lineRepository.save(line);

        return ResponseEntity
                .created(URI.create("/stations/" + persistLine.getId()))
                .body(LineResponse.of(persistLine));
    }

//    @GetMapping("/lines/{id}")
//    public ResponseEntity createLine(@PathVariable String line) {
//        return ResponseEntity
//                .created(URI.create("/stations/" + persistStation.getId()))
//                .body(StationResponse.of(persistStation));
//    }

    @GetMapping("/lines")
    public ResponseEntity showLines() {
        return ResponseEntity.ok().body(lineRepository.findAll());
    }

}
