plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ddrd.goldeskapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ddrd.goldeskapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ALTERNATIVA DE EMERGENCIA CON ANT (SI LA ANTERIOR VUELVE A DAR ERROR DE PAQUETE)
        val localPropertiesFile = rootProject.file("local.properties")
        var apiUrl = "\"http://10.0.2.2:8080/\""

        if (localPropertiesFile.exists()) {
            val antProperties = org.apache.tools.ant.taskdefs.Property()
            antProperties.project = ant.project
            antProperties.setFile(localPropertiesFile)

            // Ant carga las propiedades directamente en el proyecto
            val remoteUrl = ant.project.properties["URL_REMOTA"]
            if (remoteUrl != null) {
                apiUrl = remoteUrl.toString()
            }
        }

        buildConfigField("String", "BASE_URL", apiUrl)
    }
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit: La librería principal para las peticiones HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Converter GSON: Para convertir automáticamente el JSON del backend a objetos Java (DTOs)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp Logging Interceptor: Muy útil para ver en el Logcat qué está enviando el celular
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("com.google.android.material:material:1.11.0")
}