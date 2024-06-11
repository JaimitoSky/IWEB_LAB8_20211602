<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Creación de Película</title>
</head>
<body>
<h2>Formulario de Creación de Película</h2>
<form action="/enviar_datos" method="post">
    <label for="titulo">Título:</label><br>
    <input type="text" id="titulo" name="titulo" required><br>

    <label for="director">Director:</label><br>
    <input type="text" id="director" name="director" required><br>

    <label for="anoPublicacion">Año Publicación:</label><br>
    <input type="number" id="anoPublicacion" name="anoPublicacion" required><br>

    <label for="rating">Rating:</label><br>
    <input type="text" id="rating" name="rating" required><br>

    <label for="boxOffice">Box Office:</label><br>
    <input type="text" id="boxOffice" name="boxOffice" required><br>

    <label for="duracion">Duración:</label><br>
    <input type="text" id="duracion" name="duracion" required><br>

    <label for="genero">Género:</label><br>
    <select name="genero" id="genero">

    </select><br>

    <label for="streaming">Streaming:</label><br>
    <select name="streaming" id="streaming">

    </select><br>

    <label for="premioOscar">Premio Oscar:</label><br>
    <select name="premioOscar" id="premioOscar">
        <option value="true">Sí</option>
        <option value="false">No</option>
    </select><br>

    <input type="submit" value="Enviar">
</form>
</body>
</html>

