> Exemplo de projeto utilizando Spring Boot e Thymeleaf



# Dependências

* Spring Boot 
* Thymeleaf
* JPA
* Twitter Bootstrap

# Documentação

* http://projects.spring.io/spring-boot/
* http://www.thymeleaf.org/features.html

# Configurando Spring Boot

> Pom.xml


     <parent>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-parent</artifactId>
         <version>1.2.5.RELEASE</version>
     </parent>   

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
> Agora basta configurar a classe main do projeto onde será startado e indentifacado com um projeto spring boot.

 
     @Controller
     @SpringBootApplication
     public class HomeController {

      private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

      @RequestMapping(value = "/", method = RequestMethod.GET)
      @ResponseBody
      public String home() {
        return "hello World";
      }

      public static void main(String[] args) {
         SpringApplication.run(HomeController.class, args);
      }
  
  
# Configurando Thymeleaf

> Pom.xml

    <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		
> Após colocar a dependência bastar criar em **src/resource** o diretório **templates**. neste diretório ficará seu front-end onde você utilizará o html nativo com thymeleaf.

**Exemplo:**


      <table id="myTable" class="table table-condensed table-hover">
      	<caption>Tabela Exemplo</caption>
      	<thead>
      		<tr>
      			<th class="text-left col-md-1">Data</th>
      			<th class="text-right col-md-2">Salário (R$)</th>
      		</tr>
      	</thead>
      	<tbody>
      		<tr th:each="lista : ${controller.lista}">
      			<td th:text="${#dates.format(lista.data, 'dd/MM/yyyy')}" class="text-left col-md-1"></td>
      			<td	th:text="${#numbers.formatDecimal(lista.salario, 1, 'POINT', 2, 'COMMA')}" class="text-right"></td>
      	</tbody>
      </table>









