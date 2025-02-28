def call(String appName) {
    script {
        echo "🛑 Stopping existing instance (if running)..."
        sh "pm2 delete '${appName}' || true"

        echo "🚀 Deploying new instance..."
        sh """
            pm2 start npm --name '${appName}' -- start
            pm2 save
        """

        echo "✅ Deployment completed successfully!"
    }
}
