<?php 

include 'connect.php';




if (isset($_GET['idUsuarios']))
{
	
	$idUsuarios=$_GET['idUsuarios'];
	$sql = "select * from Tenista,Usuario where Tenista.Usuarios_idUsuarios='$idUsuarios' AND Usuario.idUsuarios=Tenista.Usuarios_idUsuarios";
}
else if (isset($_GET['idTenistas']))
{
	$idTenistas = $_GET['idTenistas'];
	$sql = "select * from Tenista,Usuario where idTenistas='$idTenistas' AND Usuario.idUsuarios=Tenista.Usuarios_idUsuarios";
}
else if (isset($_GET['idCategoria']))
{
	$idCategoria = $_GET['idCategoria'];
	$sql = "select * from Tenista,Usuario where Categoria_idCategoria='$idCategoria' AND Usuario.idUsuarios=Tenista.Usuarios_idUsuarios";
}
else 
	$sql = "select * from Tenista,Usuario where Usuario.idUsuarios=Tenista.Usuarios_idUsuarios";
	


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["tenistas"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$tenistas = array();
		$tenistas["idTenistas"] = $row["idTenistas"];
		$tenistas["idUsuarios"] = $row["Usuarios_idUsuarios"];
		$tenistas["PosicaoAtualRanking"] = $row["PosicaoAtualRanking"];
		$tenistas["Categoria"] = $row["Categoria_idCategoria"];
		$tenistas["Nome"] = $row["Nome"];
		$tenistas["Endereco"] = $row["Endereco"];
		$tenistas["Telefone"] = $row["Telefone"];
		$tenistas["Email"] = $row["Email"];
		$tenistas["NomeUsuario"]=$row["NomeUsuario"];
		$tenistas["CadastroValido"]=$row["CadastroValido"];
		$tenistas["EstaNoRanking"]=$row["EstaNoRanking"];
		
		array_push($result["tenistas"],$tenistas);
		//array_push($result,array('id'=>$row[0],'nome'=>$row[1],'endereco'=>$row[2],'telefone'=>$row[3],'email'=>$row[4],'usuario'=>$row[5],'senha'=>$row[6]));
	}
	
		$result["result"]=1;
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);

		mysqli_close($conn);

?>