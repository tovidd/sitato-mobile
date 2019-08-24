<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePengadaanSparepartTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('pengadaan_sparepart', function (Blueprint $table) {
            $table->increments('id_pengadaan_sparepart');
            $table->integer('id_cabang')->unsigned();
            $table->integer('id_supplier')->unsigned();
            $table->date('tanggal_pengadaan_sparepart');
            $table->timestamps();
            $table->softDeletes();
            //foreign
            $table->foreign('id_cabang')->references('id_cabang')->on('cabang');
            $table->foreign('id_supplier')->references('id_supplier')->on('supplier');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pengadaan_sparepart');
    }
}
