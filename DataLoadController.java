@RestController
@RequestMapping("/api/v1/load-data")
public class DataLoadController {

    private final DataLoadService dataLoadService;

    @PostMapping("/initial")
    @Operation(summary = "Inicia a carga inicial de dados de veículos da FIPE")
    @ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Processo de carga iniciado") })
    public ResponseEntity<Void> triggerInitialLoad() {
        dataLoadService.startInitialLoad();
        return ResponseEntity.accepted().build(); // Retorna 202 Accepted
    }
}