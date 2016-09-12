<?php 

include 'connect.php';


//se tipo == 1 então insere, se tipo==2 então atualiza os dados

if (isset($_POST['tipo']))
{
	$tipo = $_POST['tipo'];

}


if (isset($_POST['regras']))
{
	$regras = $_POST['regras'];
	$d=json_decode($regras,true);
	
	if ($tipo==1)
	{
		$sql = "insert into Regra(DataAlteracao, QtdDiasPorMesPodeDesafiar, QtdDiasPMesReceberDesafio, PosicaoMaximaQPodeDesafiar, 
		DesafiadorQtdPosCasoVitoria, DesafiadoQtdPosCasoVitoria, DesafiadorQtdPosCasoDerrota, 
		DesafiadoQtdPosCasoDerrota, QtdPosCaiCasoNaoDesafieMes, TempoWO, QtdPosicoesPerdeCasoWO) 
		VALUES (\"$d[DataAlteracao]\", $d[QtdDiasPorMesPodeDesafiar], $d[QtdDiasPMesReceberDesafio],
		$d[PosicaoMaximaQPodeDesafiar], $d[DesafiadorQtdPosCasoVitoria], $d[DesafiadoQtdPosCasoVitoria],
		$d[DesafiadorQtdPosCasoDerrota], $d[DesafiadoQtdPosCasoDerrota], $d[QtdPosCaiCasoNaoDesafieMes], $d[TempoWO],
		$d[QtdPosicoesPerdeCasoWO])";
	}
	else if ($tipo==2)
	{
		
		$sql = "update Regra SET idRegra = $d[idRegra],DataAlteracao=\"$d[DataAlteracao]\",
		QtdDiasPorMesPodeDesafiar=$d[QtdDiasPorMesPodeDesafiar],QtdDiasPMesReceberDesafio=$d[QtdDiasPMesReceberDesafio],
		PosicaoMaximaQPodeDesafiar=$d[PosicaoMaximaQPodeDesafiar], DesafiadorQtdPosCasoVitoria=$d[DesafiadorQtdPosCasoVitoria],
		DesafiadoQtdPosCasoVitoria=$d[DesafiadoQtdPosCasoVitoria],DesafiadorQtdPosCasoDerrota=$d[DesafiadorQtdPosCasoDerrota],
		DesafiadoQtdPosCasoDerrota=$d[DesafiadoQtdPosCasoDerrota],QtdPosCaiCasoNaoDesafieMes=$d[QtdPosCaiCasoNaoDesafieMes],
		TempoWO=$d[TempoWO],QtdPosicoesPerdeCasoWO=$d[QtdPosicoesPerdeCasoWO] where idRegra=$d[idRegra]";
	}

}


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if ($res)	
{
	//retorna o ID do campo inserido
	$result["result"]=mysqli_insert_id($conn);
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);

mysqli_close($conn);

?>