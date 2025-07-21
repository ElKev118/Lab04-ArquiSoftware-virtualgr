#!/bin/bash
set -e

echo "🚀 Desplegando VirtualGR en Kubernetes..."

# Crear namespace
echo "📁 Creando namespace..."
kubectl apply -f namespace.yaml

# Construir imagen Docker
echo "🐳 Construyendo imagen Docker..."
docker build -t virtualgr:latest ..

# Desplegar base de datos
echo "🗄️ Desplegando PostgreSQL..."
kubectl apply -f postgres-deployment.yaml

# Esperar a que PostgreSQL esté listo
echo "⏳ Esperando PostgreSQL..."
kubectl wait --for=condition=ready pod -l app=postgres -n virtualgr --timeout=300s

# Desplegar aplicación
echo "📱 Desplegando aplicación VirtualGR..."
kubectl apply -f app-deployment.yaml

# Desplegar monitoreo
echo "📊 Desplegando Prometheus..."
kubectl apply -f prometheus-deployment.yaml

echo "📈 Desplegando Grafana..."
kubectl apply -f grafana-deployment.yaml

# Esperar a que todo esté listo
echo "⏳ Esperando despliegue completo..."
kubectl wait --for=condition=ready pod -l app=virtualgr -n virtualgr --timeout=300s

# Mostrar información de acceso
echo "✅ Despliegue completado!"
echo ""
echo "🌐 URLs de acceso:"
echo "- VirtualGR API: kubectl port-forward -n virtualgr service/virtualgr-service 8080:80"
echo "- Prometheus: kubectl port-forward -n virtualgr service/prometheus-service 9090:9090"
echo "- Grafana: kubectl port-forward -n virtualgr service/grafana-service 3000:3000"
echo ""
echo "📋 Verificar estado:"
echo "kubectl get all -n virtualgr"