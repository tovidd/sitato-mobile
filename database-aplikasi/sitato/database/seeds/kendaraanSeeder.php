<?php

use Illuminate\Database\Seeder;
use sitato\kendaraan;

class kendaraanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        kendaraan::create([
            'no_plat_kendaraan'=> 'AB4160UH',
            'merek_kendaraan'=> 'Honda',
            'jenis_kendaraan'=> 'Supra fit',
        ]);
        kendaraan::create([
            'no_plat_kendaraan'=> 'Y3333FGH',
            'merek_kendaraan'=> 'Honda',
            'jenis_kendaraan'=> 'Supra X',
        ]);
        kendaraan::create([
            'no_plat_kendaraan'=> 'B6666RTY',
            'merek_kendaraan'=> 'Honda',
            'jenis_kendaraan'=> 'Supra fit',
        ]);
        kendaraan::create([
            'no_plat_kendaraan'=> 'P7777HJK',
            'merek_kendaraan'=> 'Honda',
            'jenis_kendaraan'=> 'Vario 125',
        ]);
        kendaraan::create([
            'no_plat_kendaraan'=> 'AB0000RT',
            'merek_kendaraan'=> 'Yamaha',
            'jenis_kendaraan'=> 'Mio j 125',
        ]);
        
    }
}
