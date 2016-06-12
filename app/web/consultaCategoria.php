<?php 

include 'connect.php';

if (isset($_POST['idCategoria']))
{
	$idCategoria=$_POST['idCategoria'];
	$sql = "select * from Categoria where idCategoria='$idCategoria'";
}
else if (isset($_POST['Nome']))
{
	$nome = $_POST['Nome'];
	$sql = "select * from Categoria where Nome='$nome'";
}
else 
	$sql = "select * from Categoria";
	


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["categorias"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$categorias = array();
		$categorias["idCategoria"] = $row["idCategoria"];
		$categorias["Nome"] = $row["Nome"];
		
		array_push($result["categorias"],$categorias);
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