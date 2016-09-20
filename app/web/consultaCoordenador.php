<?php 

include 'connect.php';

if (isset($_POST['idUsuario']))
{
	$idUsuarios=$_POST['idUsuario'];
	$sql = "select * from Coordenador where Usuarios_idUsuarios='$idUsuarios'";
}
else if (isset($_POST['idCoordenador']))
{
	$idCoordenador = $_POST['idCoordenador'];
	$sql = "select * from Coordenador where idCoordenadores='$idCoordenador'";
}
else 
	$sql = "select * from Coordenador";
	


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["coordenadores"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$coordenadores = array();
		$coordenadores["idCoordenador"] = $row["idCoordenadores"];
		$coordenadores["idUsuario"] = $row["Usuarios_idUsuarios"];
		
		array_push($result["coordenadores"],$coordenadores);
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