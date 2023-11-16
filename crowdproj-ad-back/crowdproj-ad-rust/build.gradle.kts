plugins {
}

val rustDir = layout.projectDirectory
val rustTargetDir: String by project
val rustIncludesDir: String by project
val rustLibDirX64: String by project
val rustLibDirArm64: String by project
val rustLibDirX64Debug: String by project
val rustLibDirArm64Debug: String by project

tasks {

    fun Exec.buildRustFor(platform: String, mode: String = "release") {
        group = "rust"
        workingDir = rustDir.asFile
        environment("TARGET_INCLUDE_DIR", rustDir.dir(rustTargetDir).toString())
        environment("PKG_CONFIG_SYSROOT_DIR", rustDir.dir("$rustTargetDir/aarch64-unknown-linux-gnu/root").toString())
        commandLine = listOfNotNull(
            "cargo",
            "build",
            if (mode == "release") "--release" else null,
            "--target=$platform",
        )
        outputs.dirs(
            rustDir.dir(rustIncludesDir),
            rustDir.dir(rustLibDirX64),
            rustDir.dir(rustLibDirArm64),
            rustDir.dir("repo-gremlin"),
            rustDir.dir("testcontainers"),
        )
    }
    val buildRustX64 by creating(Exec::class) {
        buildRustFor("x86_64-unknown-linux-gnu")
    }
    val buildRustArm64 by creating(Exec::class) {
        buildRustFor("aarch64-unknown-linux-gnu")
    }

    val buildRustX64Debug by creating(Exec::class) {
        buildRustFor("x86_64-unknown-linux-gnu", "debug")
    }
    val buildRustArm64Debug by creating(Exec::class) {
        buildRustFor("aarch64-unknown-linux-gnu", "debug")
    }

    create("build") {
        dependsOn(
            buildRustArm64,
            buildRustX64,
            buildRustArm64Debug,
            buildRustX64Debug,
        )
    }

}
