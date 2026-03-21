# Issue text (copy into openMF/ph-ee-connector-common → New issue)

**Title:** Add CI supply-chain baseline: Trivy gate + SBOM artifact + Cosign sign/verify

**Body:**

### Context
For Mifos deployments we should be able to show that artifacts are scanned for serious vulnerabilities, described with an SBOM, and (where CI allows OIDC) signed for later verification.

`ph-ee-connector-common` is a shared Java library; it does not ship a Docker image today, but we can still apply the same **supply-chain practices** on the built JAR: filesystem scan, SBOM, and Cosign **blob** signing for the SBOM file.

### Proposal
- Add `.github/workflows/supply-chain.yml` that:
  - runs `./gradlew jar`
  - fails on Trivy `CRITICAL`/`HIGH` for `build/libs` (with `ignore-unfixed: true` initially to keep signal usable)
  - uploads SPDX JSON SBOM (Syft)
  - on `push`, signs the SBOM with Cosign keyless and uploads signature + certificate
- Document verify steps in `docs/supply-chain.md`

### Acceptance criteria
- [ ] CI runs on PR and default branch
- [ ] Trivy gate is enforced (exit non-zero on configured severities)
- [ ] SBOM artifact attached to workflow run
- [ ] Cosign materials uploaded on push; verify command documented

### Note
I have a branch ready and can open a PR in small reviewable chunks if you prefer any policy tweaks (severity levels, unfixed CVEs, etc.).
