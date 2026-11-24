$ErrorActionPreference = 'Stop'
$GradleVersion = '8.9'
$ProjectRoot = Split-Path -Parent $PSScriptRoot

if ($env:GRADLE_BOOTSTRAP_DIR) {
    $BootstrapRoot = $env:GRADLE_BOOTSTRAP_DIR
} elseif ($env:ANDROID_AVD_HOME) {
    $BootstrapRoot = Join-Path $env:ANDROID_AVD_HOME 'gradle-bootstrap'
} else {
    $BootstrapRoot = Join-Path $env:USERPROFILE '.gradle\bootstrap'
}

New-Item -ItemType Directory -Force -Path $BootstrapRoot | Out-Null
$GradleHome = Join-Path $BootstrapRoot "gradle-$GradleVersion"
$ZipPath = Join-Path $BootstrapRoot "gradle-$GradleVersion-bin.zip"

if (-not (Test-Path (Join-Path $GradleHome 'bin\gradle.bat'))) {
    Write-Host "Downloading Gradle $GradleVersion from the official Gradle distribution service..."
    Invoke-WebRequest -Uri "https://services.gradle.org/distributions/gradle-$GradleVersion-bin.zip" -OutFile $ZipPath
    Expand-Archive -LiteralPath $ZipPath -DestinationPath $BootstrapRoot -Force
    Remove-Item -LiteralPath $ZipPath -Force
}

$GradleExecutable = Join-Path $GradleHome 'bin\gradle.bat'
Write-Host 'Generating the standard Gradle Wrapper files in the project...'
Push-Location $ProjectRoot
try {
    & $GradleExecutable wrapper --gradle-version $GradleVersion --distribution-type bin
    if ($LASTEXITCODE -ne 0) {
        throw "Gradle wrapper generation failed with exit code $LASTEXITCODE."
    }
} finally {
    Pop-Location
}

Write-Host ''
Write-Host 'Done. The Gradle Wrapper has been created.' -ForegroundColor Green
Write-Host 'You can now open this project in Android Studio and sync it.'
