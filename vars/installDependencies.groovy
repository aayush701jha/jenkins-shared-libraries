def call() {
    script {
        sh "npm install --unsafe-perm"
        echo "✅ Dependencies Installed"
    }
}
