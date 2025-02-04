    {{/* vim: set filetype=mustache: */}}
{{/*
Environment variables for web and worker containers
*/}}
{{- define "deployment.envs" }}
env:
  - name: SERVER_PORT
    value: "{{ .Values.image.port }}"

  - name: JAVA_OPTS
    value: "{{ .Values.env.JAVA_OPTS }}"

  - name: SPRING_PROFILES_ACTIVE
    value: "logstash"

  - name: DB_HOST
    valueFrom:
      secretKeyRef:
        name: "{{ .Values.db.secret_name }}"
        key: database_host

  - name: DB_PORT
    valueFrom:
      secretKeyRef:
        name: "{{ .Values.db.secret_name }}"
        key: database_port

  - name: DB_NAME
    valueFrom:
      secretKeyRef:
        name: "{{ .Values.db.secret_name }}"
        key: database_name

  - name: DB_USERNAME
    valueFrom:
      secretKeyRef:
        name: "{{ .Values.db.secret_name }}"
        key: database_username

  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: "{{ .Values.db.secret_name }}"
        key: database_password

  - name: APP_DB_URL
    value: "jdbc:postgresql://$(DB_HOST):$(DB_PORT)/$(DB_NAME)?user=$(DB_USERNAME)&password=$(DB_PASSWORD)"

  - name: AWS_DEFAULT_REGION
    value: "eu-west-2"

  - name: IMPORT_FILES_LOCATIONS
    value: "schedule_34_locations.xlsx"

  - name: IMPORT_FILES_PRICES_SERCO
    value: "serco_prices.xlsx"

  - name: IMPORT_FILES_PRICES_GEO
    value: "geoamey_prices.xlsx"

  - name: EXPORT_FILES_TEMPLATE
    value: "classpath:/spreadsheets/JPC_template.xlsx"

  - name: JPC_AWS_ACCESS_KEY_ID
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}-bucket
        key: access_key_id

  - name: JPC_AWS_SECRET_ACCESS_KEY
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}-bucket
        key: secret_access_key

  - name: JPC_BUCKET_NAME
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}-bucket
        key: bucket_name

  - name: BASM_BUCKET_NAME
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}
        key: BASM_BUCKET_NAME

  - name: BASM_AWS_ACCESS_KEY_ID
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}
        key: BASM_AWS_ACCESS_KEY_ID

  - name: BASM_AWS_SECRET_ACCESS_KEY
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}
        key: BASM_AWS_SECRET_ACCESS_KEY

  - name: HMPPS_AUTH_BASE_URI
    value: "{{ .Values.env.HMPPS_AUTH_BASE_URI }}"

  - name: HMPPS_AUTH_REDIRECT_BASE_URI
    value: "{{ .Values.env.HMPPS_AUTH_REDIRECT_BASE_URI }}"

  - name: HMPPS_AUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}
        key: HMPPS_AUTH_CLIENT_ID

  - name: HMPPS_AUTH_CLIENT_SECRET
    valueFrom:
      secretKeyRef:
        name: {{ template "app.name" . }}
        key: HMPPS_AUTH_CLIENT_SECRET

  - name: CRON_IMPORT_REPORTS
    value: "{{ .Values.env.CRON_IMPORT_REPORTS }}"

  - name: SENTRY_DSN
    valueFrom:
      secretKeyRef:
        name: "{{ .Values.sentry.secret_name }}"
        key: sentry_dsn

  - name: SENTRY_ENVIRONMENT
    value: "{{ .Values.env.SENTRY_ENVIRONMENT }}"

  - name: BASM_API_BASE_URL
    value: "{{ .Values.env.BASM_API_BASE_URL }}"

{{- end -}}
