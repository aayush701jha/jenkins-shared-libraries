def call() {
    script {
        sh 'rm -rf dist || true'
        sh 'npm run build'
        echo "✅ Project Built Successfully"
    }
}
