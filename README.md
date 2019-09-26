# Rent car example

An example project that demonstrates an end-to-end cloud-native platform using Spring Cloud and fabric8 for building a practical microservices architecture. 
Fabric8 plugin provides a tight integration into Maven for building Docker images and creating Kubernetes and OpenShift resource descriptors.

Include services:    
Auth service based on Oauth2    
Config service based on Spring Cloud Config 
Service Discovery based on Eureka server    
API gateway based on Zuul   
Order service   
Catalog service  
State service for manage order states

**Requirements**    
Java: JDK 1.8   
Maven Build     
Docker  
Kubernetes: Minikube on local

**Setting up the Environment**  
To play with these examples, you can install locally Kubernetes & Docker using [Minikube](https://kubernetes.io/docs/getting-started-guides/minikube/) within a Virtual Machine managed by a hypervisor (Xhyve, Virtualbox or KVM) if your machine is not a native Unix operating system.

When the minikube is installed on your machine, you can start kubernetes using this command:

minikube start  
You also probably want to configure your docker client to point the minikube docker deamon with:

eval $(minikube docker-env)     
This will make sure that the docker images that you build are available to the minikube environment.