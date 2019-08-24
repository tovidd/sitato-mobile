<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        $this->call(sparepartSeeder::class);
        $this->call(roleSeeder::class);
        $this->call(supplierSeeder::class);
        $this->call(cabangSeeder::class);
        $this->call(jasaServiceSeeder::class);
        $this->call(kendaraanSeeder::class);
        $this->call(pelangganSeeder::class);
        $this->call(statusPengerjaanSeeder::class);
        $this->call(pegawaiSeeder::class);

        $this->call(pengadaanSparepartSeeder::class);
        $this->call(detilPengadaanSparepartSeeder::class);
        $this->call(transaksiSeeder::class);
        $this->call(transaksiPenjualanJasaServiceSeeder::class);
        $this->call(transaksiPenjualanSparepartSeeder::class);
    }
}
