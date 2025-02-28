def call(String appName, String port) {
    script {
        echo "🚀 Starting new instance on port ${port}..."

        // Start the new instance
        sh """
            pm2 start npm --name '${appName}-new' -- start -- --port=${port}
            sleep 5  # Give it some time to stabilize
        """

        echo "🛑 Stopping old instance..."
        sh "pm2 delete '${appName}' || true"

        echo "🔄 Renaming new instance to ${appName}..."
        sh "pm2 restart '${appName}-new' --name '${appName}'"

        echo "💾 Saving PM2 state..."
        sh "pm2 save"

        echo "✅ Deployment completed successfully!"
    }
}
