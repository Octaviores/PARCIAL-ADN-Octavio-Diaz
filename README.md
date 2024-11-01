<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Examen Mercadolibre - Detección de Mutantes</title>
  <style>
    body { font-family: Arial, sans-serif; }
    h1, h2, h3 { color: #2c3e50; }
    code { background-color: #ecf0f1; padding: 2px 4px; font-size: 90%; }
    .code-block { background-color: #ecf0f1; padding: 10px; margin: 10px 0; border-radius: 5px; }
    a { color: #2980b9; }
  </style>
</head>
<body>

<h1>Examen Mercadolibre</h1>
<p>Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Mens.</p>
<p>Te ha contratado para desarrollar un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN.</p>

<h2>Descripción del Proyecto</h2>
<p>Se ha desarrollado un programa en Java con Spring Boot que expone un método para detectar si un humano es mutante según su secuencia de ADN. La función principal se llama:</p>
<div class="code-block">
  <code>boolean isMutant(String[] dna);</code>
</div>

<p>El parámetro de esta función es un array de Strings que representa cada fila de una matriz de (NxN) con la secuencia de ADN. Las letras de los Strings solo pueden ser <strong>A</strong>, <strong>T</strong>, <strong>C</strong>, y <strong>G</strong>, representando cada base nitrogenada del ADN.</p>

<h3>Criterios para la Detección de Mutantes</h3>
<p>Un humano es identificado como mutante si tiene más de una secuencia de cuatro letras iguales, ya sea de forma horizontal, vertical u oblicua en la matriz.</p>

<p><strong>Ejemplo de ADN mutante:</strong></p>
<div class="code-block">
  <code>String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};</code>
</div>

<p>En este caso, el método <code>isMutant(dna)</code> devuelve <code>true</code>.</p>

<h2>Niveles del Proyecto</h2>

<h3>Nivel 1</h3>
<p>Implementación del método en Java con Spring Boot, estructurado en una arquitectura de controladores, servicios y repositorios.</p>

<h3>Nivel 2</h3>
<p>Desarrollar una API REST desplegada en <a href="https://render.com/">Render</a> para detectar mutantes mediante un endpoint <code>/mutant/</code>, el cual recibe el ADN en formato JSON y devuelve un código HTTP 200 si es mutante o un 403 si no lo es.</p>
<p><strong>Ejemplo de uso:</strong></p>
<div class="code-block">
  <code>
    POST → https://parcial-adn-octavio-diaz.onrender.com/api/humanos/mutant <br>
    Body: <br>
    { <br>
      &nbsp;&nbsp;"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] <br>
    }
  </code>
</div>

<h3>Nivel 3</h3>
<p>Integración con una base de datos H2 para almacenar las secuencias de ADN analizadas, evitando registros duplicados. Exposición de un nuevo endpoint <code>/stats</code> que devuelve estadísticas de las verificaciones de ADN en JSON.</p>

<p><strong>URL para ver estadísticas:</strong></p>
<ul>
  <li><a href="https://parcial-adn-octavio-diaz.onrender.com/api/humanos/stats">https://parcial-adn-octavio-diaz.onrender.com/api/humanos/stats</a></li>
</ul>

<h2>Instrucciones para el Despliegue Local</h2>
<ol>
  <li>Descargar el repositorio como archivo ZIP.</li>
  <li>Importar dependencias en tu entorno de desarrollo.</li>
  <li>Ejecutar la aplicación desde la clase <code>ApiMutantApplication</code>.</li>
  <li>Usar Postman para hacer un POST a la URL <code>http://localhost:8080/api/humanos/mutant</code> y enviar el siguiente body de ejemplo:</li>
</ol>

<div class="code-block">
  <code>
    { <br>
      &nbsp;&nbsp;"dna": [ <br>
      &nbsp;&nbsp;&nbsp;&nbsp;"ATGCAA",<br>
      &nbsp;&nbsp;&nbsp;&nbsp;"CTGACT",<br>
      &nbsp;&nbsp;&nbsp;&nbsp;"GTATGA",<br>
      &nbsp;&nbsp;&nbsp;&nbsp;"CAGTAC",<br>
      &nbsp;&nbsp;&nbsp;&nbsp;"TGATCG",<br>
      &nbsp;&nbsp;&nbsp;&nbsp;"ACGTAG"<br>
      &nbsp;&nbsp;] <br>
    }
  </code>
</div>

<h2>Instrucciones de Code Coverage</h2>
<p>Para ejecutar las pruebas y comprobar el code coverage, ejecuta la clase <code>HumanoTest</code> en el entorno de desarrollo. Se ha logrado un <strong>coverage mayor al 80%</strong>.</p>

<h2>Pruebas de Estrés</h2>
<p>Para realizar pruebas de estrés en la API, configura y ejecuta tus pruebas en Apache JMeter.</p>
<ul>
  <li><code>/api/humanos/mutant</code>: Endpoint para realizar pruebas de carga en la detección de mutantes.</li>
  <li><code>/api/humanos/stats</code>: Endpoint para obtener estadísticas bajo pruebas de carga.</li>
</ul>

<h2>Diagrama de Secuencia</h2>
<p>A continuación se muestra un diagrama de secuencia del flujo principal de detección de mutantes:</p>
<img src="ruta/a/tu/diagrama.png" alt="Diagrama de Secuencia">

</body>
</html>
