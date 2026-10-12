# compilarMicroservicios.ps1
# Compila y empaqueta los cuatro microservicios (clean package) en esta misma ventana
# y muestra el .jar generado de cada uno.
# Uso: .\compilarMicroservicios.ps1   (desde la carpeta ENCOMIENDAS)

$raiz = Split-Path -Parent $MyInvocation.MyCommand.Path
$servicios = @("pedidos-ms", "logistica-ms", "trabajadores-ms", "autenticacion-ms")
$fallidos = @()

foreach ($servicio in $servicios) {
    $ruta = Join-Path $raiz $servicio
    Write-Host ""
    Write-Host "=== Compilando $servicio ===" -ForegroundColor Cyan

    Push-Location $ruta
    try {
        .\mvnw.cmd clean package
        if ($LASTEXITCODE -ne 0) {
            $fallidos += $servicio
        } else {
            Get-ChildItem .\target\*.jar | ForEach-Object {
                Write-Host "Generado: $($_.FullName)" -ForegroundColor Green
            }
        }
    } finally {
        Pop-Location
    }
}

Write-Host ""
if ($fallidos.Count -eq 0) {
    Write-Host "Los cuatro microservicios compilaron correctamente." -ForegroundColor Green
} else {
    Write-Host "Fallo la compilacion en: $($fallidos -join ', ')" -ForegroundColor Red
}
