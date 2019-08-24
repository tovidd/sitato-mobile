<?php

use Illuminate\Database\Seeder;
use sitato\cabang;

class cabangSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        cabang::create([
            'nama_cabang'=> 'Babarsari',
            'no_telepon_cabang'=> '082222233333',
            'alamat_cabang'=> 'Jalan babarsari nomor 1 blok 1A',
        ]);
        cabang::create([
            'nama_cabang'=> 'Demangan',
            'no_telepon_cabang'=> '082340223344',
            'alamat_cabang'=> 'Jalan demangan nomor 2 blok 2A',
        ]);
        cabang::create([
            'nama_cabang'=> 'Paingan',
            'no_telepon_cabang'=> '089595778899',
            'alamat_cabang'=> 'Jalan paingan nomor 3 blok 3A',
        ]);
        cabang::create([
            'nama_cabang'=> 'Jetis',
            'no_telepon_cabang'=> '082223556677',
            'alamat_cabang'=> 'Jalan jetis nomor 4 blok 4A',
        ]);
        cabang::create([
            'nama_cabang'=> 'Merican',
            'no_telepon_cabang'=> '089595778899',
            'alamat_cabang'=> 'Jalan merican nomor 5 blok 5A',
        ]);
    }
}