def call(Map args = [:]) {
    def timestamp = new Date().format("yyyy-MM-dd_HH-mm-ss", TimeZone.getTimeZone('UTC'))
    def backupFile = "/tmp/stage_backup_${timestamp}.sql"
    
    sh """
        PGPASSWORD="aayush" pg_dump -h 43.204.112.36 -U postgres -d stage_test_db -p 5432 > ${backupFile}
        aws s3 cp ${backupFile} s3://db-backup-pipeline/
    """
}
