package wooteco.subway.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.admin.dto.request.EdgeCreateRequest;
import wooteco.subway.admin.dto.request.LineRequest;
import wooteco.subway.admin.dto.request.LineUpdateRequest;
import wooteco.subway.admin.dto.response.LineResponse;
import wooteco.subway.admin.dto.response.StationsAtLineResponse;
import wooteco.subway.admin.service.LineService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class LineController {
    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLine(@RequestBody @Valid LineRequest request) {
        LineResponse persistLine = lineService.save(request);
        return ResponseEntity
                .created(URI.create("/stations/" + persistLine.getId()))
                .body(persistLine);
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<LineResponse> showLine(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(lineService.showLine(id));
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> showLines() {
        return ResponseEntity.ok().body(lineService.showLines());
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity<LineResponse> updateLine(@PathVariable("id") Long id, @RequestBody LineUpdateRequest request) {
        return ResponseEntity.ok().body(lineService.updateLine(id, request));
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable("id") Long id) {
        lineService.deleteLine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/lines/{lineId}/edges")
    public ResponseEntity<StationsAtLineResponse> createEdge(@PathVariable("lineId") Long lineId, @RequestBody @Valid EdgeCreateRequest request) {
        return ResponseEntity
                .created(URI.create("/edges/" + lineId))
                .body(lineService.addEdge(lineId, request));
    }

    @GetMapping("/lines/stations")
    public ResponseEntity<List<StationsAtLineResponse>> showEdges() {
        return ResponseEntity.ok().body(lineService.findEveryLineAndStation());
    }

    @DeleteMapping("/lines/{lineId}/stations/{stationId}")
    public ResponseEntity<Void> deleteEdge(@PathVariable("lineId") Long lineId, @PathVariable("stationId") Long stationId) {
        lineService.removeEdge(lineId, stationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
