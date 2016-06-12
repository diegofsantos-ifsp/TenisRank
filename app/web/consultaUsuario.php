<?php 

include 'connect.php';

if (isset($_POST['Nome']))
{
	$nome=$_POST['Nome'];
	$sql = "select * from Usuario where Nome='$nome'";
}
else if (isset($_POST['Email']))
{
	$email = $_POST['Email'];
	$sql = "select * from Usuario where Email='$email'";
}
else if (isset($_POST['idUsuarios']))
{
	$id = $_POST['idUsuarios'];
	$sql = "select * from Usuario where idUsuarios='$id'";
}
	
else if (isset($_POST['idTenistas']))
{
	$idTenistas = $_POST['idTenistas'];
	$sql = "select * from Usuario,tenista where usuario.idUsuarios=(select tenista.Usuarios_idUsuarios from tenista where tenista.idTenistas='$idTenistas') AND tenista.Usuarios_idUsuarios=usuario.idUsuarios;";
}
else 
	$sql = "select * from Usuario";
	


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["usuarios"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$usuarios = array();
		$usuarios["idUsuarios"] = $row["idUsuarios"];
		$usuarios["Nome"] = $row["Nome"];
		$usuarios["Endereco"] = $row["Endereco"];
		$usuarios["Telefone"] = $row["Telefone"];
		$usuarios["Email"] = $row["Email"];
		$usuarios["NomeUsuario"] = $row["NomeUsuario"];
		$usuarios["Senha"] = $row["Senha"];
		$usuarios["DataUltimoAcesso"] = $row["DataUltimoAcesso"];
		$usuarios["HoraUltimoAcesso"] = $row["HoraUltimoAcesso"];
		$usuarios["CadastroValido"] = $row["CadastroValido"];
		
		array_push($result["usuarios"],$usuarios);
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