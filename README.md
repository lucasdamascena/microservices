
# microservices
Projeto criado para treinar Microservices no Spring Framework com Integrações de Service Discovery com Eureka, além de Circuit Break com Resilience4j. Tive como prioridade o estudo e utilização das tecnologias, por isso, não existem testes unitários.

## Features
- Implementação de Actuator;
- Implementação de OpenFeign para Interação entre Microservices;
- Implementação de Resilience4j para Circuit Break;
- Implementação de Eureka para Service Discovery;
- Configuração de ApiGateway

## O projeto

<p>
	<img  src=content/microservice_arch.png>
</p>

## Dica

- Ainda não criei um commit sobre tracing, porém, é possível utilizar o Zupkin e Sleuth.
- Suba uma imagem docker: https://hub.docker.com/r/openzipkin/zipkin/tags?page=1&ordering=last_updated
- Atualize o pom.xml do microserviço com as dependências abaixo:

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

Para controlar as amostragens de logs, acrescente no arquivo de propriedade (Lembrando que em produção seria recomendado logar até 0.05):

```
sleuth:
	sampler:
		probability: 1.0
```