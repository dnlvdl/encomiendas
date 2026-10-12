# levantarMicroservicios.ps1
# Levanta los cuatro microservicios, cada uno en su propia ventana de PowerShell.
# Uso: .\levantarMicroservicios.ps1   (desde la carpeta ENCOMIENDAS)

$raiz = Split-Path -Parent $MyInvocation.MyCommand.Path

# Credenciales de SQL Server (se piden una vez y se heredan a cada ventana)
if (-not $env:DB_USER) {
    $env:DB_USER = Read-Host "Usuario de SQL Server"
}
if (-not $env:DB_PASSWORD) {
    $seguro = Read-Host "Clave de SQL Server" -AsSecureString
    $env:DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
        [Runtime.InteropServices.Marshal]::SecureStringToBSTR($seguro))
}

$servicios = @("pedidos-ms", "logistica-ms", "trabajadores-ms", "autenticacion-ms")

foreach ($servicio in $servicios) {
    $ruta = Join-Path $raiz $servicio
    Write-Host "Levantando $servicio en $ruta ..."
    Start-Process powershell -WorkingDirectory $ruta `
        -ArgumentList "-NoExit", "-Command", ".\mvnw.cmd spring-boot:run"
    Start-Sleep -Seconds 3
}

Write-Host ""
Write-Host "Servicios iniciados:"
Write-Host "  pedidos-ms       http://localhost:8080"
Write-Host "  logistica-ms     http://localhost:8081"
Write-Host "  trabajadores-ms  http://localhost:8082"
Write-Host "  autenticacion-ms http://localhost:8083"
Write-Host "Revisa cada ventana hasta ver la linea 'Started ...'."
