# crm-pipedrive-integration
Integração com o CRM Pipedrive (utilizado na etapa de pré-venda do funil de vendas).

# API RESTFul - Spring Boot

## Tecnologias utilizadas:

  - Java 11;
  - Spring Boot;
  - Swagger;
  - Lombok.

## Configuração

  Para conseguir rodar este projeto, é necessário:

  - Java 11 instalado na máquina;
  - Alguma IDE com extensões para suportar o framework Spring (IntelliJ, SpringToolSuite, VSCode, Eclipse, etc.);
  - Uma conta no [CRM Pipedrive](https://www.pipedrive.com/pt).

## Execução

  A aplicação possui 2 tipos de requisições expostas:
  
    - Para inserção de leads, através da URL: http://localhost:8080/api/lead
    - Para finalização de leads, através da URL: http://localhost:8080/api/lead/finaliza
    
  Também conta com um sistema de Fila, configurado para adicionar todos os Leads inseridos que não possuam um usuário disponível para atender no momento.
 
## Partes chave do código

 - Objeto utilizado para padronizar retorno: [Response](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/api/response/Response.java);

 - Annotation [@RestAPIController](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/annotation/RestAPIController.java), criada para agrupar as anotações utilizadas na APIController e para possibilitar a composição das URL's com '/api' na frente de forma automática. Classe de configuração para essa composição das URL's: [WebConfig](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/WebConfig.java);

 - Criação de um padrão de projeto para consumo de API's, com um sub-tipo específico para API's de busca por CEP, utilizando as classes: [APIClient](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/client/APIClient.java) e PipedriveAPIClient(https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/client/PipedriveAPIClient.java);

 - Classe que efetivamente faz a requisição para a API do Pipedrive, utilizando o RestTemplate do próprio Spring, reconfigurado na classe ConfigBeans e aplicando o padrão de projeto supracitado: [PipedriveConsumer](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/client/PipedriveConsumer.java);

 - Esta, por sua vez, é injetada na classe [CRMConsumer](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/client/CRMConsumer.java), padrão criado a fim de garantir uma maior flexibilidade na implementação de outras API's de CRM;

 - Regra de negócio toda concentrada na classe [LeadService](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/service/LeadService.java);

 - Todas as classes supracitadas possuem o suporte do package [util](https://github.com/gregoryfeijon/crm-pipedrive-integration/tree/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/util), que possui implementações padrões necessárias para a execução da API;

 - Configurações feitas por meio de properties, a fim de garantir uma maior flexibilidade na configuração da aplicação e na mudança entre perfis. A injeção de properties adotadas foi via @Bean, por se encaixar melhor na necessidade do que o @Value, deixando o código mais limpo. Configuração desses Beans realizada também na classe [ConfigBeans](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/ConfigBeans.java);

 - Criação da [fila de leads](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/service/FilaLeads.java), que é manipulada na service de acordo com a necessidade, através da classe: [ConsumerFilaLeads](https://github.com/gregoryfeijon/crm-pipedrive-integration/blob/main/src/main/java/br/com/gregoryfeijon/crmpipedriveintegration/service/ConsumerFilaLeads.java). 

