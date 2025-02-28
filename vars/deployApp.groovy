def call(String appName) {
    script {
        sh """
            if pm2 list | grep -q ${appName}; then
                pm2 reload ${appName} --update-env
            else
                pm2 start npm --name '${appName}' -- start --watch -i max
            fi
            pm2 save
        """
        echo "🚀 Zero-Downtime Rolling Deployment Done for '${appName}'!"
    }
}
