<?php 

include 'connect.php';


//retorna se o tenista possui algum jogo marcado. Se PORJOGAR =1 então somente jogos não jogados ainda. Se =0, todos os jogos jogados
//menos os que estão por serem jogados
if (isset($_GET['idTenistas']) && isset($_GET['PORJOGAR']))
{
	$idTenistas = $_GET['idTenistas'];
	$PORJOGAR = $_GET['PORJOGAR'];
	if ($PORJOGAR)
		$sql = "select * from Desafio where (TenistaDesafiado='$idTenistas' OR TenistaDesafiador_idTenistas='$idTenistas') AND Jogado=0 AND AceitoDesafiado=1 AND AceitoDesafiador=1 ORDER BY DATA DESC";
	else
		$sql = "select * from Desafio where (TenistaDesafiado='$idTenistas' OR TenistaDesafiador_idTenistas='$idTenistas') ORDER BY DATA DESC";
//	else if ($PORJOGAR && getdadostenistas)
//	$sql = "select * from Desafio where (TenistaDesafiado='$idTenistas' OR TenistasDesafiador_idTenistas='$idTenistas') AND Jogado=0 AND AceitoDesafiado=1 AND AceitoDesafiador=1";
	//	$sql = "";
}

//retorna todos os jogos do tenista (jogados ou não)
else if (isset($_GET['idTenistas']))
{
	//$idCategoria = $_GET['idCategoria'];
	$idTenistas = $_GET['idTenistas'];
	
	$sql = "select * from Desafio where (TenistaDesafiado='$idTenistas' OR TenistaDesafiador_idTenistas='$idTenistas') ORDER BY DATA DESC";
}
else 
	$sql = "select * from Desafio ORDER BY DATA DESC";
	

	
$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["desafios"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$desafios = array();
		$desafios["idDesafios"] = $row["idDesafios"];
		$desafios["TenistaDesafiador_idTenistas"] = $row["TenistaDesafiador_idTenistas"];
		$desafios["TenistaDesafiado"] = $row["TenistaDesafiado"];
		$desafios["Quadra_idQuadra"] = $row["Quadra_idQuadra"];
		$desafios["Data"] = $row["Data"];
		$desafios["Hora"] = $row["Hora"];
		$desafios["Jogado"] = $row["Jogado"];
		$desafios["AceitoDesafiado"] = $row["AceitoDesafiado"];
		$desafios["AceitoDesafiador"] = $row["AceitoDesafiador"];
		$desafios["Descricao"]=$row["Descricao"];
		$desafios["ResultTenistaDesafiado1"]=$row["ResultTenistaDesafiado1"];
		$desafios["ResultTenistaDesafiado2"]=$row["ResultTenistaDesafiado2"];
		$desafios["ResultTenistaDesafiado3"]=$row["ResultTenistaDesafiado3"];
		$desafios["ResultTenistaDesafiador1"]=$row["ResultTenistaDesafiador1"];
		$desafios["ResultTenistaDesafiador2"]=$row["ResultTenistaDesafiador2"];
		$desafios["ResultTenistaDesafiador3"]=$row["ResultTenistaDesafiador3"];
		$desafios["WO"]=$row["WO"];
		$desafios["ConfirmadoCoordenador"]=$row["ConfirmadoCoordenador"];
		$desafios["ConfirmadoDesafiado"]=$row["ConfirmadoDesafiado"];
		$desafios["ConfirmadoDesafiador"]=$row["ConfirmadoDesafiador"];
		$desafios["tieBreakDesafiado1"]=$row["tieBreakDesafiado1"];
		$desafios["tieBreakDesafiado2"]=$row["tieBreakDesafiado2"];
		$desafios["tieBreakDesafiado3"]=$row["tieBreakDesafiado3"];
		$desafios["tieBreakDesafiador1"]=$row["tieBreakDesafiador1"];
		$desafios["tieBreakDesafiador2"]=$row["tieBreakDesafiador2"];
		$desafios["tieBreakDesafiador3"]=$row["tieBreakDesafiador3"];
		
		
		
		
		array_push($result["desafios"],$desafios);
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