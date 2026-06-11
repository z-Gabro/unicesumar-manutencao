<?php

namespace App\Services;

use App\Agendamento;
use Exception;

class GoogleCalendarService
{
    public function sync(Agendamento $agendamento)
    {
        try {
            $evento = [
                'summary' => optional($agendamento->orcamento)->tatuagem_nome,
                'description' => optional($agendamento->orcamento)->tatuagem_descricao,
                'location' => optional($agendamento->estacao)->identificacao,
                'start' => $agendamento->data_horario_inicio,
                'end' => $agendamento->data_horario_fim,
            ];

            // Simulação de envio para API externa
            // Em produção aqui entraria a integração real

            return [
                'success' => true,
                'event' => $evento
            ];
        } catch (Exception $e) {
            throw new Exception(
                'Erro ao sincronizar com Google Calendar: ' .
                $e->getMessage()
            );
        }
    }
}
