<?php 

include 'connect.php';


//retorna se o tenista possui algum jogo marcado. Se PORJOGAR =1 então somente jogos não jogados ainda. Se =0, todos os jogos jogados
//menos os que estão por serem jogados


if (isset($_POST['tipo']))
{
	$tipo = $_POST['tipo'];
}

if (isset($_POST['idDesafio']))
{
	$idDesafio = $_POST['idDesafio'];
}

if (isset($_POST['desafio']))
{
	$desafio = $_POST['desafio'];
	$d=json_decode($desafio,true);
	
	if ($tipo==1)
	{
		$sql = "insert into Desafio (TenistaDesafiador_idTenistas,TenistaDesafiado,Quadra_idQuadra,Data,Hora,Jogado,AceitoDesafiado,
			AceitoDesafiador,Descricao,ResultTenistaDesafiado1,ResultTenistaDesafiado2,ResultTenistaDesafiado3,
			ResultTenistaDesafiador1,ResultTenistaDesafiador2,ResultTenistaDesafiador3,WO,ConfirmadoCoordenador,ConfirmadoDesafiado,
			ConfirmadoDesafiador,tieBreakDesafiado1,tieBreakDesafiador1,tieBreakDesafiado2,tieBreakDesafiador2,tieBreakDesafiado3,
			tieBreakDesafiador3,Ganhador, DesafiadorPontosSeGanhar, DesafiadorPontosSePerder, DesafiadoPontosSeGanhar,
			DesafiadoPontosSePerder, EstaNoRanking) values ($d[idTenistaDesafiador],$d[idTenistaDesafiado],$d[idQuadra],\"$d[Data]\",
			\"$d[Hora]\",$d[Jogado],$d[AceitoDesafiado],$d[AceitoDesafiador],\"$d[Descricao]\",$d[ResultTenistaDesafiado1],
			$d[ResultTenistaDesafiado2],$d[ResultTenistaDesafiado3],$d[ResultTenistaDesafiador1],$d[ResultTenistaDesafiador2],
			$d[ResultTenistaDesafiador3],$d[WO],$d[ConfirmadoCoordenador],$d[ConfirmadoDesafiado],$d[ConfirmadoDesafiador],
			$d[tieBreakDesafiado1],$d[tieBreakDesafiador1],$d[tieBreakDesafiado2],$d[tieBreakDesafiador2],$d[tieBreakDesafiado3],
			$d[tieBreakDesafiador3],$d[Ganhador], $d[DesafiadorPontosSeGanhar],$d[DesafiadorPontosSePerder],$d[DesafiadoPontosSeGanhar],
			$d[DesafiadoPontosSePerder],$d[EstaNoRanking])";
	}
	else if ($tipo==2)
	{
		
		$sql = "update Desafio SET TenistaDesafiador_idTenistas = $d[idTenistaDesafiador],TenistaDesafiado=$d[idTenistaDesafiado],
		Quadra_idQuadra=$d[idQuadra],Data=\"$d[Data]\",Hora=\"$d[Hora]\",Jogado=$d[Jogado],AceitoDesafiado=$d[AceitoDesafiado],
		AceitoDesafiador=$d[AceitoDesafiador],Descricao=\"$d[Descricao]\",ResultTenistaDesafiado1=$d[ResultTenistaDesafiado1],
		ResultTenistaDesafiado2=$d[ResultTenistaDesafiado2],ResultTenistaDesafiado3=$d[ResultTenistaDesafiado3],
		ResultTenistaDesafiador1=$d[ResultTenistaDesafiador1],ResultTenistaDesafiador2=$d[ResultTenistaDesafiador2],
		ResultTenistaDesafiador3=$d[ResultTenistaDesafiador3],WO=$d[WO],ConfirmadoCoordenador=$d[ConfirmadoCoordenador],
		ConfirmadoDesafiado=$d[ConfirmadoDesafiado],
		ConfirmadoDesafiador=$d[ConfirmadoDesafiador],tieBreakDesafiado1=$d[tieBreakDesafiado1],
		tieBreakDesafiador1=$d[tieBreakDesafiador1],tieBreakDesafiado2=$d[tieBreakDesafiado2],
		tieBreakDesafiador2=$d[tieBreakDesafiador2],tieBreakDesafiado3=$d[tieBreakDesafiado3],
		tieBreakDesafiador3=$d[tieBreakDesafiador3],Ganhador=$d[Ganhador],DesafiadorPontosSeGanhar=$d[DesafiadorPontosSeGanhar],
		DesafiadorPontosSePerder=$d[DesafiadorPontosSePerder], DesafiadoPontosSeGanhar=$d[DesafiadoPontosSeGanhar],
		DesafiadoPontosSePerder=$d[DesafiadoPontosSePerder],EstaNoRanking=$d[EstaNoRanking]	where idDesafios=$d[idDesafio]";
	}

}


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if ($res)	
{
	$result["result"]=1;
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);

mysqli_close($conn);

?>