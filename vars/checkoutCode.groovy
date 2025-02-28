def call(String repoUrl, String branch, String credentialsId) {
    script {
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            userRemoteConfigs: [[
                credentialsId: credentialsId,
                url: repoUrl
            ]]
        ])
        echo "✅ Checked out branch '${branch}' from '${repoUrl}'"
    }
}
