<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateDetilPengadaanSparepartTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('detil_pengadaan_sparepart', function (Blueprint $table) {
            $table->increments('id_detil_pengadaan_sparepart');
            $table->integer('id_pengadaan_sparepart')->unsigned();
            $table->integer('id_sparepart')->unsigned();
            $table->string('satuan_detil_pengadaan_sparepart');
            $table->integer('jumlah_beli_detil_pengadaan_sparepart');
            $table->float('subtotal_detil_pengadaan_sparepart');
            $table->timestamps();
            $table->softDeletes();
            //foreign
            $table->foreign('id_pengadaan_sparepart')->references('id_pengadaan_sparepart')->on('pengadaan_sparepart');
            $table->foreign('id_sparepart')->references('id_sparepart')->on('sparepart');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('detil_pengadaan_sparepart');
    }
}

