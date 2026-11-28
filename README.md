# Math Quest: Brain Challenge

## Project Framework Inspection

This repository is a **Native Android Application** (not a web application):

- **Language:** Kotlin (`2.2.10`)
- **UI Framework:** Jetpack Compose (Material Design 3)
- **Build System:** Gradle (Kotlin DSL — `build.gradle.kts`, `settings.gradle.kts`)
- **Local Persistence:** AndroidX Room (`2.7.0`)
- **Monetization & Services:** Google Mobile Ads (AdMob), Google Play Billing (`billing-ktx`), Google Services / Firebase BoM (`firebase-ai`, `firebase-appcheck`)
- **Application ID:** `com.aistudio.mathquest.bqxzlv`

---

## Important Note on Vercel Deployment

**Vercel only hosts web applications** (such as React + Vite, Next.js, static HTML/JS/CSS, or Node.js/serverless APIs). Vercel's build environment does not provide the Android SDK/NDK to build `.apk`/`.aab` binaries, and browsers cannot natively execute compiled Android Kotlin/Jetpack Compose apps without rewriting the app into a web framework (e.g., React + Vite or Compose Multiplatform for Web).

Because the existing UI, features, functionality, and native Android codebase have been preserved without converting frameworks:
- **For Android distribution:** Build a signed `.apk` or `.aab` using Gradle and publish to Google Play Console, Firebase App Distribution, or GitHub Releases.
- **If you want a Vercel-hosted web version in the future:** A separate React + Vite (or Next.js) web client would need to be created in its own directory/repo (with build command `npm run build`, install command `npm install`, and output directory `dist`).

---

## Security & Credentials

- Never commit `.env`, `local.properties`, `*.jks`, `*.keystore`, or `*.apk`/`*.aab` files to GitHub.
- `.gitignore` is configured to exclude signing keys (`my-upload-key.jks`, `*.keystore`), `.env`, build folders, and APK/AAB outputs.
- AdMob and API secrets should be provided via environment variables or AI Studio Secrets / CI secrets, never hardcoded.

### Required Environment Variables (for Signed Release Builds)

| Variable | Description |
| --- | --- |
| `KEYSTORE_PATH` | Path to your upload keystore (e.g., `./my-upload-key.jks`) |
| `STORE_PASSWORD` | Keystore password |
| `KEY_PASSWORD` | Key alias (`upload`) password |
| `GEMINI_API_KEY` | Optional — only if Gemini API integration is enabled via Secrets Gradle Plugin |

---

## Local Build & Verification Commands

### Debug Build
```bash
gradle assembleDebug
```
Output APK: `app/build/outputs/apk/debug/app-debug.apk`

### Signed Release APK
```bash
STORE_PASSWORD=your_store_password \
KEY_PASSWORD=your_key_password \
KEYSTORE_PATH=/absolute/path/to/my-upload-key.jks \
gradle assembleRelease
```
Output APK: `app/build/outputs/apk/release/app-release.apk`

### Signed App Bundle (AAB for Google Play Console)
```bash
STORE_PASSWORD=your_store_password \
KEY_PASSWORD=your_key_password \
KEYSTORE_PATH=/absolute/path/to/my-upload-key.jks \
gradle bundleRelease
```
Output AAB: `app/build/outputs/bundle/release/app-release.aab`

### Unit Tests
```bash
gradle :app:testDebugUnitTest --tests com.example.MathQuestGameTest
```

---

## Pushing to GitHub

```bash
git init
git status   # Confirm *.jks, *.apk, .env, and build/ are ignored
git add .
git commit -m "Math Quest Android project"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```
