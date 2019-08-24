<?php

use Illuminate\Database\Seeder;
use sitato\statusPengerjaan;

class statusPengerjaanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        statusPengerjaan::create([
            'nama_status_pengerjaan'=> 'sedang dikerjakan'
        ]);
        statusPengerjaan::create([
            'nama_status_pengerjaan'=> 'selesai dikerjakan'
        ]);
    }
}
