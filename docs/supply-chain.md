# Supply chain checks (CI)

This repository runs a GitHub Actions workflow **Supply chain** (`.github/workflows/supply-chain.yml`) that:

1. **Builds** the library JAR with Gradle.
2. **Scans** `build/libs` with [Trivy](https://github.com/aquasecurity/trivy) for `CRITICAL` and `HIGH` issues (unfixed CVEs are ignored to reduce noise; adjust in the workflow if needed).
3. **Generates** an SPDX JSON SBOM of the built artifacts with [Syft](https://github.com/anchore/syft) via [sbom-action](https://github.com/anchore/sbom-action).
4. **Signs** the SBOM with [Cosign](https://docs.sigstore.dev/cosign/overview/) in **keyless** mode on `push` events (not on `pull_request` from forks where OIDC may be unavailable).

Artifacts are attached to the workflow run: `sbom-spdx-json`, and on push also `sbom-cosign-bundle`.

## Verify a downloaded SBOM signature locally

After downloading `sbom.spdx.json`, `sbom.spdx.json.sig`, and `sbom.spdx.json.cert` from the workflow artifacts:

```bash
cosign verify-blob --certificate sbom.spdx.json.cert --signature sbom.spdx.json.sig sbom.spdx.json
```

## References

- [SLSA](https://slsa.dev/)
- [Sigstore / Cosign](https://docs.sigstore.dev/)
