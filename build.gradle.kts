plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.51"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    description {
        contributors {
            name("Peng_Lx")
            name("Bkm016")
        }
        dependencies {
            name("ModelEngine")
        }
    }
    install("common")
    install("common-5")
    install("module-database")
    install("platform-bukkit")
    install("module-configuration")
    install("module-nms")
    install("module-nms-util")
    install("module-lang")
    install("module-kether")
    install("expansion-command-helper")
    classifier = null
    version = "6.0.10-31"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v11900:11900:all-mapped")
    compileOnly("ink.ptms.core:v11701:11701-minimize:mapped")
    compileOnly("ink.ptms.core:v11701:11701-minimize:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}