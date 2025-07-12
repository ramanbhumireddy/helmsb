
power shell // choco install kubernetes-helm  // install helm
helm history myapp  // deployd history which contains unique numer of revision
helm rollback myapp 1(revision num)
helm install myapp ./myapp -f values-dev.yml
helm install myapp-chart sbhelm
helm create name app-folder-name(app)

helm upgrade my-app ./my-chart -f values.yaml

minikube service [service-name] --url  // this opens tunnel url use to test.

tree name

helm list

minikube -p minikube docker-env | Invoke-Expression					  \\ power shell
@FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env') DO @%i   //cmd
eval $(minikube -p minikube docker-env)   // git bash
minikube image ls

helm uninstall myapp-chart-helmsb --namespace default   // to stop entire helm deployment.


helm create microservices-chart/charts/service-discovery
helm create microservices-chart/charts/user-service
helm create microservices-chart/charts/order-service
helm create microservices-chart/charts/api-gateway



----------------

microservices-chart/
├── Chart.yaml
├── values.yaml
├── charts/
│   ├── service-discovery/
│   ├── user-service/
│   ├── order-service/
│   └── api-gateway/
🛠 1. Create Main Helm Chart
Run:

bash
Copy
Edit
helm create microservices-chart
Then delete boilerplate templates:

bash
Copy
Edit
rm -rf microservices-chart/templates/*
Update microservices-chart/Chart.yaml:

yaml
Copy
Edit
apiVersion: v2
name: microservices-chart
description: A Helm chart for all Spring Boot microservices
version: 0.1.0

dependencies:
  - name: service-discovery
    version: 0.1.0
    repository: file://charts/service-discovery
  - name: user-service
    version: 0.1.0
    repository: file://charts/user-service
  - name: order-service
    version: 0.1.0
    repository: file://charts/order-service
  - name: api-gateway
    version: 0.1.0
    repository: file://charts/api-gateway
🛠 2. Create Subcharts
You can run this 4 times:

bash
Copy
Edit
helm create microservices-chart/charts/service-discovery
helm create microservices-chart/charts/user-service
helm create microservices-chart/charts/order-service
helm create microservices-chart/charts/api-gateway
Delete extra templates in each, and keep only:

Copy
Edit
templates/
  └── deployment.yaml
  └── service.yaml
🧩 3. Customize deployment.yaml (for all services)
Example for service-discovery:

yaml
Copy
Edit
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ include "service-discovery.fullname" . }}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: {{ include "service-discovery.name" . }}
  template:
    metadata:
      labels:
        app: {{ include "service-discovery.name" . }}
    spec:
      containers:
        - name: service-discovery
          image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
          ports:
            - containerPort: 8080
          env:
            - name: EUREKA_CLIENT_REGISTER_WITH_EUREKA
              value: "false"
            - name: EUREKA_CLIENT_FETCH_REGISTRY
              value: "false"
For other services, set:

yaml
Copy
Edit
- name: EUREKA_URI
  value: "http://service-discovery:8080/eureka/"
⚙️ 4. values.yaml (per service)
For example, charts/service-discovery/values.yaml:

yaml
Copy
Edit
image:
  repository: your-dockerhub/service-discovery
  tag: latest
Same pattern for other services.

🧠 5. Common values (optional)
In microservices-chart/values.yaml you can override:

yaml
Copy
Edit
user-service:
  image:
    repository: your-dockerhub/user-service
    tag: latest

order-service:
  image:
    repository: your-dockerhub/order-service
    tag: latest

api-gateway:
  image:
    repository: your-dockerhub/api-gateway
    tag: latest

service-discovery:
  image:
    repository: your-dockerhub/service-discovery
    tag: latest
🚀 6. Deploy to Kubernetes
bash
Copy
Edit
cd microservices-chart
helm dependency update
helm install my-microservices .
✅ Optional: Ingress or LoadBalancer
To expose api-gateway externally, add to api-gateway/templates/service.yaml:

yaml
Copy
Edit
spec:
  type: LoadBalancer
Or add Ingress config with NGINX controller.

Would you like me to generate a downloadable .zip of this full Helm chart setup for you?