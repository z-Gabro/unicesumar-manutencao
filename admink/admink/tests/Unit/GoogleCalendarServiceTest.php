<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\Services\GoogleCalendarService;
use App\Agendamento;
use App\Orcamento;
use App\Estacao;

class GoogleCalendarServiceTest extends TestCase
{
    public function test_sync_success()
    {
        $agendamento = new Agendamento([
            'data_horario_inicio' => '2025-06-10 10:00:00',
            'data_horario_fim' => '2025-06-10 12:00:00',
        ]);

        $orcamento = new Orcamento([
            'tatuagem_nome' => 'Dragão',
            'tatuagem_descricao' => 'Tatuagem oriental'
        ]);

        $estacao = new Estacao([
            'identificacao' => 'Sala 01'
        ]);

        $agendamento->setRelation('orcamento', $orcamento);
        $agendamento->setRelation('estacao', $estacao);

        $service = new GoogleCalendarService();

        $result = $service->sync($agendamento);

        $this->assertTrue($result['success']);
        $this->assertEquals('Dragão', $result['event']['summary']);
    }

    public function test_sync_failure()
    {
        $this->expectException(\TypeError::class);

        $service = new GoogleCalendarService();

        $service->sync(null);
    }
}