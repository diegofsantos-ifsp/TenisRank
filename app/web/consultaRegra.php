<?php 

include 'connect.php';

	$sql = "select * from Regra";
	


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["regras"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$coordenadores = array();
		$coordenadores["idRegra"] = $row["idRegra"];
		$coordenadores["DataAlteracao"] = $row["DataAlteracao"];
		$coordenadores["QtdDiasPorMesPodeDesafiar"] = $row["QtdDiasPorMesPodeDesafiar"];
		$coordenadores["QtdDiasPMesReceberDesafio"] = $row["QtdDiasPMesReceberDesafio"];
		$coordenadores["PosicaoMaximaQPodeDesafiar"] = $row["PosicaoMaximaQPodeDesafiar"];
		$coordenadores["DesafiadorQtdPosCasoVitoria"] = $row["DesafiadorQtdPosCasoVitoria"];
		$coordenadores["DesafiadoQtdPosCasoVitoria"] = $row["DesafiadoQtdPosCasoVitoria"];
		$coordenadores["DesafiadorQtdPosCasoDerrota"] = $row["DesafiadorQtdPosCasoDerrota"];
		$coordenadores["DesafiadoQtdPosCasoDerrota"] = $row["DesafiadoQtdPosCasoDerrota"];
		$coordenadores["QtdPosCaiCasoNaoDesafieMes"] = $row["QtdPosCaiCasoNaoDesafieMes"];
		$coordenadores["TempoWO"] = $row["TempoWO"];
		$coordenadores["QtdPosicoesPerdeCasoWO"] = $row["QtdPosicoesPerdeCasoWO"];
		
		
		
		array_push($result["regras"],$coordenadores);
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