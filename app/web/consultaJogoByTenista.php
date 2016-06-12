<?php 

include 'connect.php';

if (isset($_GET['GETDADOSTENISTAS']))
	$getdadostenistas = $_GET['GETDADOSTENISTAS'];

//retorna se o tenista possui algum jogo marcado. Se PORJOGAR =1 então somente jogos não jogados ainda. Se =0, todos os jogos jogados
//menos os que estão por serem jogados
if (isset($_GET['idTenistas']) && isset($_GET['PORJOGAR']))
{
	$idTenistas = $_GET['idTenistas'];
	$PORJOGAR = $_GET['PORJOGAR'];
	if ($PORJOGAR)
		$sql = "select * from Desafio where (TenistaDesafiado='$idTenistas' OR TenistasDesafiador_idTenistas='$idTenistas') AND Jogado=0 AND AceitoDesafiado=1 AND AceitoDesafiador=1";
//	else 
	//	$sql = "";
}

//retorna todos os jogos do tenista (jogados ou não)
else if (isset($_GET['idTenistas']))
{
	$idCategoria = $_GET['idCategoria'];
	$sql = "select COUNT(*) from Desafio where (TenistaDesafiado='$idTenistas' OR TenistasDesafiador_idTenistas='$idTenistas')";
}
else 
	$sql = "select * from Desafio";
	

	
$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["desafios"]=array();


/*
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
		
		array_push($result["tenistas"],$tenistas);
		//array_push($result,array('id'=>$row[0],'nome'=>$row[1],'endereco'=>$row[2],'telefone'=>$row[3],'email'=>$row[4],'usuario'=>$row[5],'senha'=>$row[6]));
	}*/
	
		$result["result"]=1;
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);

		mysqli_close($conn);

?>