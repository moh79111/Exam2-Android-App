# Manual Test Checklist

- [ ] Empty email: Login disabled.
- [ ] Invalid email: Login disabled.
- [ ] Valid email + password shorter than six characters: Login disabled.
- [ ] Valid email + password with six or more characters: Login enabled.
- [ ] Tap Login: ModeActivity opens.
- [ ] Swipe from First to Second and Third.
- [ ] Tap each tab title and confirm the corresponding page appears.
- [ ] Back from Third: returns to First.
- [ ] Back from Second: returns to First.
- [ ] Back from First: returns to MainActivity.
- [ ] Tap Set Mode and choose Light, Night, and System; verify the theme changes.
- [ ] Tap toolbar Up from ModeActivity: returns directly to MainActivity.
- [ ] On MainActivity, press Back once: Snackbar says exactly `Press back again to exit`.
- [ ] Press Back again within 2.5 seconds: app task exits.
