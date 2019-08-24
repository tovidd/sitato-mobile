<?php

use Illuminate\Database\Seeder;
use sitato\jasaService;

class jasaServiceSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        jasaService::create([
            'id_jasa_service'=> 1,
            'kode_jasa_service'=> 1,
            'nama_jasa_service'=> 'Service mesin',
            'harga_jasa_service'=> 200000,
        ]);
        jasaService::create([
            'id_jasa_service'=> 2,
            'kode_jasa_service'=> 2,
            'nama_jasa_service'=> 'Service kampas',
            'harga_jasa_service'=> 30000,
        ]);
        jasaService::create([
            'id_jasa_service'=> 3,
            'kode_jasa_service'=> 3,
            'nama_jasa_service'=> 'Service ban',
            'harga_jasa_service'=> 20000,
        ]);
        jasaService::create([
            'id_jasa_service'=> 4,
            'kode_jasa_service'=> 4,
            'nama_jasa_service'=> 'Service lampu',
            'harga_jasa_service'=> 10000,
        ]);
    }
}
