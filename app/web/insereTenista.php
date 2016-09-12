<?php 

include 'connect.php';



if (isset($_POST['tipo']))
{
	$tipo = $_POST['tipo'];
}

if (isset($_POST['idTenista']))
{
	$idTenista = $_POST['idTenista'];
}

if (isset($_POST['tenista']))
{
	$tenista = $_POST['tenista'];
	$d=json_decode($tenista,true);
	
	if ($tipo==1)
	{
		$sql = "insert into Tenista (Usuarios_idUsuarios,PosicaoAtualRanking,Categoria_idCategoria,EstaNoRanking) values 
		($d[idUsuarios],$d[PosicaoAtualRanking],$d[idCategoria],$d[EstaNoRanking])";
	}
	else if ($tipo==2)
	{
		
		$sql = "update Desafio SET idTenistas = $d[idTenistas],Usuarios_idUsuarios=$d[idUsuarios],
		PosicaoAtualRanking=$d[PosicaoAtualRanking],Categoria_idCategoria=$d[idCategoria],EstaNoRanking=$d[EstaNoRanking]
			where idTenistas=$d[idTenistas]";
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