<?php 

include 'connect.php';


//tipo==1 insere, tipo==2 altera
if (isset($_POST['tipo']))
{
	$tipo = $_POST['tipo'];
}

if (isset($_POST['idCategoria']))
{
	$idCategoria = $_POST['idCategoria'];
}

if (isset($_POST['categorias']))
{
	$categorias = $_POST['categorias'];
	$d=json_decode($categorias,true);
	
	if ($tipo==1)
	{
		$sql = "insert into Categoria (Nome) values (\"$d[Nome]\")";
	}
	else if ($tipo==2)
	{
		
		$sql = "update Desafio SET idCategoria=$d[idCategoria],Nome=\"$d[Nome]\" where idCategoria=$d[idCategoria]";
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