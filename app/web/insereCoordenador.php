<?php 

include 'connect.php';



if (isset($_POST['tipo']))
{
	$tipo = $_POST['tipo'];
}

if (isset($_POST['idCoordenador']))
{
	$idCoordenador = $_POST['idCoordenador'];
}

if (isset($_POST['coordenadores']))
{
	$coordenadores = $_POST['coordenadores'];
	$d=json_decode($coordenadores,true);
	
	if ($tipo==1)
	{
		$sql = "insert into Coordenador (Usuarios_idUsuarios) values ($d[idUsuario])";
	}
	else if ($tipo==2)
	{
		
		$sql = "update Coordenador SET idCoordenadores = $d[idCoordenador],Usuarios_idUsuarios=$d[idUsuario] where idCoordenadores=$d[idCoordenador]";
	}

}


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if ($res)	
{
	$result["result"]=mysqli_insert_id($conn);
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);

mysqli_close($conn);

?>