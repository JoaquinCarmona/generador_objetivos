<%--
  Created by IntelliJ IDEA.
  User: ssamn
  Date: 04/06/2019
  Time: 09:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>

    <!-- para usar los estilos de boostrap-->
    <link href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet" />

    <style>

        .tableTitle,
        .mainActivity{
            border: solid 1px black;
            padding: 5px;
        }

        .mainActivity{
            margin-top: 5px;
        }

        .rowActivity{
            border-bottom: dashed 1px black;
            padding-top: 5px;
            padding-bottom: 5px;
        }

        .tableTitle{
            font-size:  16px;
            font-weight: bold ;
            color: Black;
            text-align: center;
        }

        .mainActivity{
            font-size:  14px;
            font-weight: bold ;
            font-style: italic;
            text-align: left;
            color: Black;
        }

        .tableCell{
            font-size:  12px;
            text-align: left;
        }

        .cellRol{
            font-weight: bold;
            font-style: italic;
        }

    </style>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Generador de Objetivos de Aprendizaje</a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
<%--                    <li><a href="/Home/About">Acerca de</a></li>--%>
<%--                    <li><a href="/Home/Contact">Contacto</a></li>--%>
                </ul>
                <ul class="nav navbar-nav navbar-right">
<%--                    <li><a id="registerLink" href="/Account/Register">Registrarse</a></li>--%>
<%--                    <li><a id="loginLink" href="/Account/Login">Iniciar sesión</a></li>--%>
                </ul>

            </div>
        </div>
    </div>
    <div class="container" style="position: relative; top:50px;">
        <h2>Cargar Archivo</h2>
        <div class="row">
            <form method="post" action="loadFile" enctype="multipart/form-data" class="form-horizontal col-md-12">
                <div class="form-group">
                    <label for="file" class="col-md-2 control-label">Seleccione Archivo:</label>
                    <div class="col-md-6">
                        <input id="file" name="file" type="file" class="form-control" accept="application/pdf">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-8 col-md-2">
                        <input type="submit" class="btn btn-default" value="Cargar Archivo">
                    </div>
                </div>
                <hr />
                <br />
                ${html}
<%--                <span style="color: red; font-size: 14px;">${msg}</span>--%>
<%--                <br />--%>
<%--                <textarea class="form-control" cols="100" rows="10">${html}</textarea>--%>
            </form>
        </div>
    </div>
    <!-- boostrap tiene dependencia con jquery-->
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>
